
class UTXOPool():

##    /**
##     * The current collection of UTXOs, with each one mapped to its corresponding transaction output
##     */
##    private HashMap<UTXO, Transaction.Output> H;
    H = {}
##    /** Creates a new empty UTXOPool */
    def __init__(self):
        self.H = {} #UTXO, Transaction.Output
    
##    /** Creates a new UTXOPool that is a copy of {@code uPool} */
##    public UTXOPool(UTXOPool uPool) {
##        H = new HashMap<UTXO, Transaction.Output>(uPool.H);
##    }
##
##    /** Adds a mapping from UTXO {@code utxo} to transaction output @code{txOut} to the pool */
##    public void addUTXO(UTXO utxo, Transaction.Output txOut) {
    def put(self, utxo, txOut):
        self.H[utxo] = txOut
    

##    /** Removes the UTXO {@code utxo} from the pool */
##    public void removeUTXO(UTXO utxo) {
    def remove(self, utxo):
         self.H.remove(utxo)
##    
##
##    /**
##     * @return the transaction output corresponding to UTXO {@code utxo}, or null if {@code utxo} is
##     *         not in the pool.
##     */
    def getTxOutput(self, ut):
        return self.H.get(ut);
    

##    /** @return true if UTXO {@code utxo} is in the pool and false otherwise */
    def contains(self, utxo):
        return self.H.containsKey(utxo)
    

##    /** Returns an {@code ArrayList} of all UTXOs in the pool */
    def getAllUTXO(self):
        setUTXO = self.H.keys();
        allUTXO = []
        for ut in setUTXO:
            allUTXO.add(ut)
        return allUTXO;
    
