class UTXO():

##    /** Hash of the transaction from which this UTXO originates */
    txHash = None

##    /** Index of the corresponding output in said transaction */
    index = None

##    /**
##     * Creates a new UTXO corresponding to the output with index <index> in the transaction whose
##     * hash is {@code txHash}
##     */
    def __init__(self, txHash, index):
        self.txHash = txHash;
        self.index = index;
    

##    /** return the transaction hash of this UTXO */
    def getTxHash(self):
        return self.txHash
    

##    /** @return the index of this UTXO */
    def getIndex(self):
        return self.index
    

##    /**
##     * Compares this UTXO to the one specified by {@code other}, considering them equal if they have
##     * {@code txHash} arrays with equal contents and equal {@code index} values
##     */
    def equals(self,other):
        if (other == None):
            return False
        if (type(self) != type(other)):
            return False
        hash = other.txHash
        ind = other.index
        if (len(hash) != len(self.txHash) or self.index != ind):
            return False
        for i in range(len(hash)):
            if (hash[i] != self.txHash[i]):
                return False
        
        return True
    
##
##    /**
##     * Simple implementation of a UTXO hashCode that respects equality of UTXOs // (i.e.
##     * utxo1.equals(utxo2) => utxo1.hashCode() == utxo2.hashCode())
##     */
    def hashCode(self):
        hash = 1;
        hash = hash * 17 + index;
        hash = hash * 31 + hash(txHash);
        return hash
    

##    /** Compares this UTXO to the one specified by {@code utxo} */
    def compareTo(self,utxo):
        hash = utxo.txHash
        ind = utxo.index
        if (ind > self.index):
            return -1;
        elif (ind < self.index):
            return 1;
        else:
            len1 = len(self.txHash)
            len2 = len(hash)
            if (len2 > len1):
                return -1;
            elif (len2 < len1):
                return 1
            else:
                for i in range(len1):
                    if (hash[i] > self.txHash[i]):
                        return -1
                    elif (hash[i] < self.txHash[i]):
                        return 1
                
                return 0
            
        
    
