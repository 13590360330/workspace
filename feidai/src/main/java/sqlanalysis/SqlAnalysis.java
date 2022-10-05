package sqlanalysis;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@Data
public class SqlAnalysis {

    private SqlanalysisBean sqlanalysis;

    public static void main(String[] args) throws IOException {

//        String s = FileUtils.readFileToString( new File( "D:\\workspace\\feidai\\src\\main\\resources\\example.sql" ) );


        File file = FileUtil.file("C:\\Users\\Administrator\\Desktop\\11\\11.txt");
        String s1 = FileTypeUtil.putFileType( "ffd8ffe000104a464946", "jpg" );
        System.out.println(s1);

    }

}
