
package com.java.web.core.migration.executables.v1_0;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.migration.MigrationBase;
import java.sql.SQLException;

/**
 *
 * @author Jovica
 */
public class v1_0_2_CreateModule extends MigrationBase{
    
    @Override
    public void up() throws SQLException{
        this.update(StringHelper.joinNewLine(
                
"CREATE TABLE `menuitem` (",
"`menuitemid` int(11) NOT NULL AUTO_INCREMENT,",
"`menuitemname` varchar(100) CHARACTER SET utf8 NOT NULL,",
"`menuitemaction` varchar(100) DEFAULT NULL,",
"`cls` varchar(64) DEFAULT NULL,",
"`menuitemidref` int(11) DEFAULT NULL,",
"PRIMARY KEY (`menuitemid`),",
"CONSTRAINT `FK_menuitem` FOREIGN KEY (`menuitemidref`) REFERENCES `menuitem` (`menuitemid`)",
") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                ));
    }

    @Override
    public void down() throws SQLException {
        this.update(StringHelper.joinNewLine(
"DROP TABLE IF EXISTS `menuitem`;"
                ));
    }
    
    
    
}
