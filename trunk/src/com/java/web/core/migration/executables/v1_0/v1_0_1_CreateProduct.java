
package com.java.web.core.migration.executables.v1_0;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.migration.MigrationBase;
import java.sql.SQLException;

/**
 *
 * @author Jovica
 */
public class v1_0_1_CreateProduct extends MigrationBase{
    
    @Override
    public void up() throws SQLException{
        this.update(StringHelper.joinNewLine(
                
"CREATE TABLE `product` (",
"`productid` int(10) unsigned NOT NULL AUTO_INCREMENT,",
"`connstring` varchar(200) NOT NULL,",
"`url` varchar(100) NOT NULL,",
"`isproduction` tinyint(1) NOT NULL,",
"`dbusername` varchar(100) NOT NULL,",
"`dbpassword` varchar(100) NOT NULL,",
"`isactive` tinyint(1) NOT NULL,",
"`gipsprodid` int(10) NOT NULL,",
"`stylesfolder` varchar(100) NOT NULL,",
"PRIMARY KEY (`productid`),",
"UNIQUE KEY `IDX_Url` (`url`)",
") ENGINE=InnoDB DEFAULT CHARSET=latin1;"

//"insert  into `Product`(`ProductId`,`ConnString`,`URL`,`IsProduction`,`DbUsername`,`DbPassword`,`IsActive`,`GipsProdID`,`StylesFolder`) values (1,'jdbc:mysql://91.199.63.194:3306/webwiz_togservice?autoReconnect=true&useUnicode=true&characterEncoding=utf8','togservice.hlomobil.no',1,'webwiz','RybFzWqLpGGtrBzY',0,0,''),(2,'jdbc:mysql://91.199.63.10:3306/webwiz_demo?autoReconnect=true&useUnicode=true&characterEncoding=utf8','demo.hlomobil.no',1,'webwiz','RybFzWqLpGGtrBzY',0,0,''),(3,'jdbc:mysql://localhost:3306/webwiz_demo?autoReconnect=true&useUnicode=true&characterEncoding=utf8','localhost',0,'webwiz','RybFzWqLpGGtrBzY',0,0,''),(4,'jdbc:mysql://91.199.63.10:3306/webwiz_evo?autoReconnect=true&useUnicode=true&characterEncoding=utf8','evo.gipsmobile.com',1,'webwiz','RybFzWqLpGGtrBzY',0,0,''),(5,'jdbc:mysql://91.199.63.10:3306/webwiz_webcomputing?autoReconnect=true&useUnicode=true&characterEncoding=utf8','webcomputing.gipsmobile.com',1,'webwiz','RybFzWqLpGGtrBzY',0,0,'');"
                ));
    }

    @Override
    public void down() throws SQLException {
        this.update(StringHelper.joinNewLine(
"DROP TABLE IF EXISTS `Product`;"

                ));
    }
    
    
    
}
