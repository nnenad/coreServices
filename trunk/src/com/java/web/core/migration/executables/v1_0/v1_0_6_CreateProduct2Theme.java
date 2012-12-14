
package com.java.web.core.migration.executables.v1_0;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.migration.MigrationBase;
import java.sql.SQLException;

/**
 *
 * @author Jovica
 */
public class v1_0_6_CreateProduct2Theme extends MigrationBase{
    
    @Override
    public void up() throws SQLException{
        this.update(StringHelper.joinNewLine(
                
"CREATE TABLE `product2theme` (",
"`productid` int(10) unsigned NOT NULL,",
"`themeid` int(10) NOT NULL,",
"`isactive` tinyint(1) NOT NULL,",
"PRIMARY KEY (`productid`,`themeid`),",
"CONSTRAINT `FK_Product2Theme` FOREIGN KEY (`themeid`) REFERENCES `theme` (`themeid`),",
"CONSTRAINT `FK_Product2Theme1` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`)",
") ENGINE=InnoDB DEFAULT CHARSET=latin1;"

                ));
    }

    @Override
    public void down() throws SQLException {
        this.update(StringHelper.joinNewLine(
"DROP TABLE IF EXISTS `Product2Theme`;"

                ));
    }
    
    
    
}
