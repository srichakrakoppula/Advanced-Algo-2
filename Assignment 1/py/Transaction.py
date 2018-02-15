

class Transaction():

    class Input():
        def __init__(self, prevHash, index):
            if (prevHash == None):
                self. prevTxHash = None
            else:
                self.prevTxHash = prevHash
            self.outputIndex = index

        def addSignature(self, sig):
            if (sig == null):
                self.signature = null;
            else:
                self.signature = sig
        

    class Output():
        def __init__(self, v, pk):
            self.value = v;
            self.address = pk
        

    def __init__(self, tx = None):
        self.inputs = []
        self.outputs = []
        if tx != None:
            self.hash = tx.hash

    def addInput(prevTxHash, outputIndex):
        inp = Input(prevTxHash, outputIndex)
        inputs.add(inp);
    

    def addOutput(value, address):
        op = Output(value, address)
        outputs.add(op)
    
    def removeInput(index):
        inputs.remove(index)
    

    def removeInput(ut):
        for u in inputs:
            if (u.equals(ut)):
                inputs.remove(u)
            

    def getRawDataToSign(index):
        # produces data repr for  ith=index input and all outputs
        sigData = ""
        if (index > len(self.inputs)):
            return None
        inp = inputs.get(index)
        prevTxHash = inp.prevTxHash
        sigData += inp.outputIndex
        sigData += prevTxHash
        for op in self.outputs:
            sigData += str(op.value)
            sigData += str(op.address)
        return sigData
    

    def addSignature(self, signature, index):
        self.inputs.get(index).addSignature(signature)
    

    def getRawTx(self):
        rawTx = ""
        for inp in self.inputs:
            rawTx += inp.prevTxHash
            rawTx += inp.outputIndex
            rawTx += inp.signature
        
        for op in self.outputs:
            rawTx += str(op.value)
            rawTx += op.address

        return rawTx


    def finalize(self):
        import hashlib
        md = hashlib.sha256()
        md.update(getRawTx())
        self.hash = md.hexdigest()

    def getInput(self, index):
        if (index < len(self.inputs)):
            return self.inputs.get(index)
        return None
    

    def getOutput(self, index):
        if (index < len(self.outputs)):
            return self.outputs.get(index)
        return None
    
