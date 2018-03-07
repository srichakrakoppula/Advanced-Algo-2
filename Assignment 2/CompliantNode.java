import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {

	boolean[] followees;		// Graph connection; these nodes send this node txs
	Set<Transaction> validTxs;	// This nodes starting set; assumed to be valid
	
	Set<Transaction> allTxs;	// The set of all transactions

	HashMap<Transaction, Set<Integer>> newCandidates; // all candidate Txs received from followees
	

	final int numRounds;
	final double p_malicious;
	final double p_txDistribution;
	
	int round;
	int numFollowees;
	int seenThreshold;
	
    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        // IMPLEMENT THIS
    	this.p_malicious = p_malicious;
    	this.numRounds = numRounds;
    	this.p_txDistribution = p_txDistribution;
    	
    	round = 0;
    	numFollowees = 0;
    	seenThreshold = 0;
    	
    	newCandidates = new HashMap<Transaction, Set<Integer>>();
    }

    /** {@code followees[i]} is true if and only if this node follows node {@code i} */
    public void setFollowees(boolean[] followees) {
        // IMPLEMENT THIS
    	this.followees = followees;
    	
    	for (boolean f: followees) {
    		if (f)
    			++numFollowees;
    	}
    	
    	seenThreshold = (int)(numFollowees * p_malicious);	// truncate decimal value
    	
    	//System.err.println("Followees: " + numFollowees);
    	//System.err.println("Seen Threshold: " + seenThreshold);
    }

    /** initialize proposal list of transactions */
    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        // IMPLEMENT THIS
    	allTxs = validTxs = pendingTransactions;
    }

    /**
     * @return proposals to send to my followers. REMEMBER: After final round, behavior of
     *         {@code getProposals} changes and it should return the transactions upon which
     *         consensus has been reached.
     */
    public Set<Transaction> sendToFollowers() {
        // IMPLEMENT THIS
    	if (round < numRounds)
    		return validTxs;
    	else {
    		//System.err.println("returning valid Txs: " + validTxs.size() + ", allTxs: " + allTxs.size());
    		return validTxs;
    	}
    }

    /** receive candidates from other nodes. */
    public void receiveFromFollowees(Set<Candidate> candidates) {
        // IMPLEMENT THIS
    	++round;
    	
    	// A Candidate transaction is valid if its id is in the list of validTxs
    	// build by the simulator (which this node does not know). 
    	//
    	// A Candidate Tx is valid if:
    	// it's Hash matches that of a known valid Tx
    	// two or more nodes have proposed it 
    	// (or some fraction of the followees proposed it)
    	
    	// Alg0. Everything is good if it comes from a followee
    	for (Candidate c: candidates) {
    		if (followees[c.sender])
    			validTxs.add(c.tx);
    	}
    	
    	//
    	// Alg1. 
    	// The initial Txs are all valid
    	// A candidate Tx is held in a buffer if it's not on the valid list
    	// and it has only been seen from one followee.
    	// A candidate from some threshold number of followees is assumed valid
    	// Only Txs assumed valid are sent to followers
    	/*
    	for (Candidate c: candidates) {
    		if (!followees[c.sender])
    			continue;
    		
    		// if this candidate tx on the validTxs list, leave it there.
    		// if not, add it to the newTxs list
    		if (!validTxs.contains(c.tx)) {
    			Set<Integer> senders = newCandidates.get(c.tx);
    			if (senders == null) {
    				senders = new HashSet<Integer>();
    				senders.add(c.sender);
    				newCandidates.put(c.tx, senders);
    			}
    			else if (!senders.contains(c.sender)) {
    				senders.add(c.sender);
    				newCandidates.put(c.tx, senders);
    			}
    		}
    	}
    	
    	// Test for candidate Txs from more than one other node 
    	for (Transaction tx: newCandidates.keySet()) {
    		Set<Integer> senders = newCandidates.get(tx);
    		if (senders.size() >= seenThreshold) {
    			allTxs.add(tx);
    			validTxs.add(tx);
    		}
    		else if (senders.size() >= (int)(seenThreshold * p_txDistribution)) {
    			// The difference between this and broadcasting all txs was nil
        		allTxs.add(tx);
    		}
    	}
    	*/
    }
}