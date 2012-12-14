
package com.java.web.core.migration.executables.v1_0;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.migration.MigrationBase;
import java.sql.SQLException;

/**
 *
 * @author Jovica
 */
public class v1_0_4_CreateCustomStyle extends MigrationBase{
    
    @Override
    public void up() throws SQLException{
        this.update(StringHelper.joinNewLine(
                
"CREATE TABLE `customstyle` (",
"`styleid` int(10) unsigned NOT NULL,",
"`foldername` varchar(100) CHARACTER SET utf8 NOT NULL,",
"`productid` int(10) unsigned NOT NULL,",
"PRIMARY KEY (`styleid`),",
"CONSTRAINT `FK_CustomStyle` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`)",
") ENGINE=InnoDB DEFAULT CHARSET=latin1;"

                ));
    }

    @Override
    public void down() throws SQLException {
        this.update(StringHelper.joinNewLine(
"DROP TABLE IF EXISTS `CustomStyle`;"

                ));
    }
    
    
    
}
