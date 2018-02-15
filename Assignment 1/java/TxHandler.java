import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.security.PublicKey;
import java.util.Set;

public class TxHandler {

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */

    private UTXOPool pool;

    public TxHandler(UTXOPool utxoPool) {
      this.pool = new UTXOPool(utxoPool);
    }

    public boolean validate(UTXOPool pool, Transaction tx) {
      return (tx!=null) && (inputValid(pool, tx) >= outputValid(tx));
    }

    private double inputValid(UTXOPool pool, Transaction tx) {
      Set<UTXO> used = new HashSet<>();
      double sumIn = 0;

      for (int i = 0; i < tx.numInputs(); i++) {
        Transaction.Input input = tx.getInput(i);
        if (input == null) {
          return -1; //Stop empty transactions
        }

        UTXO utxo = new UTXO(input.prevTxHash, input.outputIndex);

        if (!pool.contains(utxo) || used.contains(utxo)) {
          return -1; //Stops duplicate UTXO
        }

        Transaction.Output prevTxOut = pool.getTxOutput(utxo);


        RSAKey pubKey = prevTxOut.address;
        byte[] message = tx.getRawDataToSign(i);
        byte[] signature = input.signature;

        //Verify Signature
        if (!pubKey.verifySignature(message, signature)) {
          return -1; //Stops Incorrect signatures
        }

        used.add(utxo);
        sumIn += prevTxOut.value;
      }
      return sumIn;
    }

    private double outputValid(Transaction tx) {
        double sumOut = 0;
        for (int i = 0; i < tx.numOutputs(); i++) {
            Transaction.Output out = tx.getOutput(i);
            if (out.value < 0) {
                return Double.MAX_VALUE; //So sumIn always <
            }
            sumOut += out.value;
        }
        return sumOut;
    }


    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid,
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     * values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
      return (tx!=null) && (inputValid(pool, tx) >= outputValid(tx));
    }

    private ConcurrentHashMap<byte[], Transaction> getTxMap(Transaction[] possibleTxs) {
      ConcurrentHashMap<byte[], Transaction> txs = new ConcurrentHashMap<>();

      for (Transaction tx : possibleTxs) {
          if (tx == null) {
              continue;
          }

          tx.finalize();
          txs.put(tx.getHash(), tx);
      }
      return txs;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */

    public Transaction[] handleTxs(Transaction[] possibleTxs) {
      if (possibleTxs == null) {
        return new Transaction[0];
      }

      ConcurrentHashMap<byte[], Transaction> txs = getTxMap(possibleTxs);
      ArrayList<Transaction> valid = new ArrayList<>();
      int txCount;


      while (true) {
          txCount = txs.size();
          for (Transaction tx : txs.values()) {
              valid.add(tx);
              this.applyTx(tx);
              txs.remove(tx.getHash());
          }

          if (txCount == txs.size() || txCount == 0) {
              break;
          }
      }

      return valid.toArray(new Transaction[valid.size()]);
    }

    private void applyTx(Transaction tx) {
      if (tx == null) {
        return;
      }

      for (Transaction.Input input : tx.getInputs()) {
        UTXO utxo = new UTXO(input.prevTxHash, input.outputIndex);
        this.pool.removeUTXO(utxo);
      }

      byte[] txHash = tx.getHash();
      int transactionIdx = 0;
      for (Transaction.Output output : tx.getOutputs()) {
        UTXO utxo = new UTXO(txHash, transactionIdx);
        transactionIdx += 1;
        this.pool.addUTXO(utxo, output);
      }
    }
}
