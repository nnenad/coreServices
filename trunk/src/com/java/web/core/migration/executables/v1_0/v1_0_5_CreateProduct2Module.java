
package com.java.web.core.migration.executables.v1_0;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.migration.MigrationBase;
import java.sql.SQLException;

/**
 *
 * @author Jovica
 */
public class v1_0_5_CreateProduct2Module extends MigrationBase{
    
    @Override
    public void up() throws SQLException{
        this.update(StringHelper.joinNewLine(
                
"CREATE TABLE `product2menuitem` (",
"`productid` int(10) unsigned NOT NULL,",
"`menuitemid` int(10) NOT NULL,",
"`isactive` tinyint(1) NOT NULL,",
"PRIMARY KEY (`productid`,`menuitemid`),",
"CONSTRAINT `FK_Product2Menuitem` FOREIGN KEY (`menuitemid`) REFERENCES `menuitem` (`menuitemid`),",
"CONSTRAINT `FK_Product2Menuitem1` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`)",
") ENGINE=InnoDB DEFAULT CHARSET=latin1;"

                ));
    }

    @Override
    public void down() throws SQLException {
        this.update(StringHelper.joinNewLine(
"DROP TABLE IF EXISTS `Product2Module`;"

                ));
    }
    
    
    
}
