final public class Transaction {
    final int id;
    final String addr;

    public Transaction(int id, String addr) {
        this.id = id;
        this.addr = addr;
    }

    @Override
    /**  @return true if this Transaction has the same id as {@code obj} */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override public int hashCode() {
        return id;
    }

}