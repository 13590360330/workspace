package com.dao;

import com.domain.SqlContent;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.List;

public class CreateOracleScript {
    public void createScript(List<SqlContent> xlsxContent) {
        Velocity.init();
        Template template = Velocity.getTemplate( "velocity\\template.sql", "utf8" );
        VelocityContext ctx = new VelocityContext();

        String filePah = "../BigData/src/resources/tmp/sql";
        try {
            FileUtils.deleteDirectory( new File( filePah ) );
            FileUtils.forceMkdir( new File( filePah ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

        xlsxContent.stream().forEach( x -> {
            ctx.put( "targettable", x.getTargetTablename() );
            ctx.put( "selectCondition", x.getSelectCondition() );
            ctx.put( "fromCondition", x.getFromCondition() );
            if (!x.getWhereCondition().isEmpty() || x.getWhereCondition().matches( "^[\\s]+$" )) {
                ctx.put( "whereCondition", x.getWhereCondition() );
            }

            int i = 0;
            String path = filePah + "/" + x.getTargetTablename() + "-part" + i + ".sql";
            File sqlFile = new File( path );
            while (sqlFile.exists()) {
                i++;
                path = filePah + "/" + x.getTargetTablename() + "-part" + i + ".sql";
                sqlFile = new File( path );
            }

            try (FileOutputStream fileOutputStream
                         = new FileOutputStream( sqlFile, true );
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter( fileOutputStream, "UTF-8" );
                 PrintWriter writer = new PrintWriter( new BufferedWriter( outputStreamWriter ) )
            ) {
                template.merge( ctx, writer );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
    }
}