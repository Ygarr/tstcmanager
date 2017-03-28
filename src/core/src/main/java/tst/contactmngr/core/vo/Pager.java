package tst.contactmngr.core.vo;

/**
 * Pager VO
 * @author ghost
 *
 */
public final class Pager {

    /**
     * Default page number
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    
    /**
     * Default page size
     */
    public static final int DEFAULT_PAGE_SIZE = 10; 
    
    /**
     * Page size
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    /**
     * Page number (first page = 1)
     */
    private int pageNum = 1;

    /**
     * Total page count
     */
    private int pageCount = 0;
    
    /**
     * Record count
     */
    private int recordCount = 0;
    
    /**
     * Hidden default constructor
     */
    @SuppressWarnings("unused")
    private Pager(){
        
    } 
    
    /**
     * Parameterized constructor
     * 
     * @param pageNum int - current page (first = 1)
     * @param pageSize int - page size (default={@link #DEFAULT_PAGE_SIZE})
     */
    public Pager(int pageNum, int pageSize){
        if(pageNum < 1){
            this.pageNum = DEFAULT_PAGE_NUM;
        } else {
            this.pageNum = pageNum;
        }
        if(pageSize <=0){
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }
    
    /**
     * Returns offset
     * 
     * @return int - offset
     */
    public int getRecordOffset() {
        if(pageNum == 1){
            return  0;
        } else {
            return (pageNum-1) * pageSize;
        }
    }

    /**
     * Returns page size
     * 
     * @return int - page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Returns page number (first page = 1)
     * 
     * @return int - page number
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * Returns total record count
     * 
     * @return int - total record count
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * Returns total page count
     * @return int - total page count
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Sets total record count
     * 
     * @param recordCount int - total record count
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        this.pageCount = recordCount / pageSize;
        if((recordCount % pageSize) != 0){
            this.pageCount = getPageCount() + 1;
        }
        if(this.pageNum > this.pageCount){
            this.pageNum = this.pageCount;
        }
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + pageCount;
        result = prime * result + pageNum;
        result = prime * result + pageSize;
        result = prime * result + recordCount;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pager other = (Pager) obj;
        if (pageCount != other.pageCount)
            return false;
        if (pageNum != other.pageNum)
            return false;
        if (pageSize != other.pageSize)
            return false;
        if (recordCount != other.recordCount)
            return false;
        return true;
    }

    public String toString() {
        return "Pager [pageSize="
                + pageSize + ", pageNum=" + pageNum + ", pageCount="
                + pageCount + ", recordCount=" + recordCount + "]";
    }

}
