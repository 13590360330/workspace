//package com.dao;
//
//import com.domain.SqlContent;
//import com.domain.basics.Column;
//import org.apache.commons.io.FileUtils;
//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;
//
//import java.io.*;
//import java.util.List;
//import java.util.Optional;
//
//public class CreateKettleScript {
//
//    public void createScript(List<SqlContent> xlsxContent
//            , String db_name_out, String ip_out, String sqltype_out, String sid_out, String port_out, String user_out, String pswd_out
//            , String db_name_in, String ip_in, String sqltype_in, String sid_in, String port_in, String user_in, String pswd_in) {
//
//        Velocity.init();
//        Template template = Velocity.getTemplate( "velocity\\kettle.ktr", "utf8" );
//        VelocityContext ctx = new VelocityContext();
//
//        String filePah = "../BigData/src/resources/tmp/kettle";
//        try {
//            FileUtils.deleteDirectory( new File( filePah ) );
//            FileUtils.forceMkdir( new File( filePah ) );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        xlsxContent.stream().forEach( x -> {
//            ctx.put( "db_name_out", db_name_out );
//            ctx.put( "ip_out", ip_out );
//            ctx.put( "sqltype_out", sqltype_out );
//            ctx.put( "sid_out", sid_out );
//            ctx.put( "port_out", port_out );
//            ctx.put( "user_out", user_out );
//            ctx.put( "pswd_out", pswd_out );
//            ctx.put( "db_name_in", db_name_in );
//            ctx.put( "ip_in", ip_in );
//            ctx.put( "sqltype_in", sqltype_in );
//            ctx.put( "sid_in", sid_in );
//            ctx.put( "port_in", port_in );
//            ctx.put( "user_in", user_in );
//            ctx.put( "user_in", pswd_in );
//            ctx.put( "table_name", x.getTargetTablename() );
//            ctx.put( "selectCondition", x.getSelectCondition().replaceAll( "\n", "\n" + String.format( "%13s", " " ) ) );
//            ctx.put( "fromCondition", x.getFromCondition().replaceAll( "\n", "\n" + String.format( "%13s", " " ) ) );
//            if (!x.getWhereCondition().isEmpty() || x.getWhereCondition().matches( "^[\\s]+$" )) {
//                ctx.put( "whereCondition", x.getWhereCondition().replaceAll( "\n", "\n" + String.format( "%13s", " " ) ) );
//            }
//            StringBuffer field = new StringBuffer();
//            Optional fields = x.getColumn().stream().reduce( (m, n) -> {
//                if (m instanceof Column) {
//                    field.append( "<field>\n" );
//                    field.append( String.format( "%12s", " " ) + "<column_name>" + ((Column) m).getColumnname() + "</column_name>\n" );
//                    field.append( String.format( "%12s", " " ) + "<stream_name>" + ((Column) m).getColumnname() + "</stream_name>\n" );
//                    field.append( String.format( "%10s", " " ) + "</field>\n" );
//                }
//                field.append( String.format( "%10s", " " ) + "<field>\n" );
//                field.append( String.format( "%12s", " " ) + "<column_name>" + ((Column) n).getColumnname() + "</column_name>\n" );
//                field.append( String.format( "%12s", " " ) + "<stream_name>" + ((Column) n).getColumnname() + "</stream_name>\n" );
//                field.append( String.format( "%10s", " " ) + "</field>\n" );
//                return field;
//            } );
//            if (fields.isPresent()) {
//                ctx.put( "fields", fields.get() );
//            }
//
//            int i = 0;
//            String path = filePah + "/" + x.getTargetTablename() + "-part" + i + ".ktr";
//            File sqlFile = new File( path );
//            while (sqlFile.exists()) {
//                i++;
//                path = filePah + "/" + x.getTargetTablename() + "-part" + i + ".ktr";
//                sqlFile = new File( path );
//            }
//
//            try (FileOutputStream fileOutputStream
//                         = new FileOutputStream( sqlFile, true );
//                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter( fileOutputStream, "UTF-8" );
//                 PrintWriter writer = new PrintWriter( new BufferedWriter( outputStreamWriter ) )
//            ) {
//                template.merge( ctx, writer );
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } );
//    }
//}
