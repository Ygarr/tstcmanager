package tst.contactmngr.core.vo;

/**
 * Contact VO
 * 
 * @author ghost
 *
 */
public class Contact {

    /**
     * Contact ID
     */
    private Long id = null;
    
    /**
     * Contact name
     */
    private String name = null;
    
    /**
     * Contact phone number
     */
    private String phoneNumber = null;

    /**
     * Default constructor
     */
    public Contact(){
        
    }
    
    /**
     * Parameterized constructor with all fields
     * 
     * @param id {@link Long} unique contact ID
     * @param name {@link String} contact name
     * @param phoneNumber {@link String} contact phone number
     */
    public Contact(
            Long id
          , String name
          , String phoneNumber
          ) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Parameterized constructor with fields except ID
     * 
     * @param name {@link String} contact name
     * @param phoneNumber {@link String} contact phone number
     */
    public Contact(
              String name
            , String phoneNumber
            ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Returns contact ID
     * @return {@link Long} contact ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets contact ID
     * 
     * @param id {@link Long} contact ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns contact name
     * 
     * @return {@link String} contact name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets contact name
     * 
     * @param name {@link String} contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns contact phone number
     * @return {@link String} contact phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets contact phone number
     * 
     * @param phoneNumber {@link String} contact phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Contact other = (Contact) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        return true;
    }

    /**
     * Compares object with this contact ignoring state (without {@link #id} field value)
     * 
     * @param obj {@link Object} other object
     * 
     * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
     */
    public boolean equalsIgnoreState(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Contact other = (Contact) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        return true;
    } 
    
    public String toString() {
        return "Contact [id=" + id + ", name=" + name + ", phoneNumber="
                + phoneNumber + "]";
    }
    
}
