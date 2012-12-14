
package com.java.web.core.migration.executables.v1_0;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.migration.MigrationBase;
import java.sql.SQLException;

/**
 *
 * @author Jovica
 */
public class v1_0_3_CreateTheme extends MigrationBase{
    
    @Override
    public void up() throws SQLException{
        this.update(StringHelper.joinNewLine(
                
"CREATE TABLE `theme` (",
"`themeid` int(10) NOT NULL,",
"`themename` varchar(100) CHARACTER SET utf8 NOT NULL,",
"PRIMARY KEY (`themeid`)",
") ENGINE=InnoDB DEFAULT CHARSET=latin1;"

                ));
    }

    @Override
    public void down() throws SQLException {
        this.update(StringHelper.joinNewLine(
"DROP TABLE IF EXISTS `Theme`;"

                ));
    }
    
    
    
}
