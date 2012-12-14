package com.java.web.core.domain.access;
// Generated Oct 4, 2011 11:16:51 AM by Hibernate Tools 3.2.1.GA



/**
 * DbVersionId generated by hbm2java
 */
public class DbVersionId  implements java.io.Serializable {


     private String ver;
     private String migType;

    public DbVersionId() {
    }

    public DbVersionId(String ver, String migType) {
       this.ver = ver;
       this.migType = migType;
    }
   
    public String getVer() {
        return this.ver;
    }
    
    public void setVer(String ver) {
        this.ver = ver;
    }
    public String getMigType() {
        return this.migType;
    }
    
    public void setMigType(String migType) {
        this.migType = migType;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DbVersionId) ) return false;
		 DbVersionId castOther = ( DbVersionId ) other; 
         
		 return ( (this.getVer()==castOther.getVer()) || ( this.getVer()!=null && castOther.getVer()!=null && this.getVer().equals(castOther.getVer()) ) )
 && ( (this.getMigType()==castOther.getMigType()) || ( this.getMigType()!=null && castOther.getMigType()!=null && this.getMigType().equals(castOther.getMigType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getVer() == null ? 0 : this.getVer().hashCode() );
         result = 37 * result + ( getMigType() == null ? 0 : this.getMigType().hashCode() );
         return result;
   }   


}


