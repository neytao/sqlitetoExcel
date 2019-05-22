import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class sqlsolve {

    public static WritableWorkbook workbook;
    public static String xlspath="";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, WriteException {
        //获取当前工作目录
        String dbpath="";
        File DirectoryPath= new File(new File("").getAbsolutePath());
        File[] files=DirectoryPath.listFiles();
        for(File file:files){
            if(file.getName().endsWith(".db")){
                dbpath="jdbc:sqlite:"+file.getPath();
                xlspath=file.getPath().substring(0,file.getPath().length()-2)+"xls";
                workbook = Workbook.createWorkbook(new File(xlspath));
                //获取表名
                String [] sqls =new String[100];
                String []sheetnames=new String[100];
                Class.forName("org.sqlite.JDBC");
                Connection connection= DriverManager.getConnection(dbpath);
                DatabaseMetaData metaData=connection.getMetaData();
                ResultSet rs=metaData.getTables(null, null, null,new String[] { "TABLE" });
                int biaoji=0;
                while (rs.next()) {
                    sheetnames[biaoji]=rs.getString("TABLE_NAME");
                    sqls[biaoji]="SELECT * FROM " + sheetnames[biaoji];
                    biaoji++;
                }

                connection.close();

                for(int i=0;i<sqls.length&&sqls[i]!=null;i++){
                    ArrayList<ArrayList<String>> result=sqlsolve.savearray(dbpath,sqls[i]);
                    sqlsolve.saveExcel(result,i,sheetnames[i],dbpath,sqls[i]);
                }
                //所有Sheet创建完毕再统一写文件
                workbook.write();
                workbook.close();

            }
        }

    }

    public static ArrayList<ArrayList<String>> savearray(String dbpath,String sql) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection= DriverManager.getConnection(dbpath);
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery(sql);
        //获取列名
        ResultSetMetaData metaData=rs.getMetaData();
        String [] col_name=new String[metaData.getColumnCount()];
        for(int i=0;i<metaData.getColumnCount();i++){
            col_name[i]=metaData.getColumnName(i+1);
        }
        //获取列名结束
        ArrayList<ArrayList<String>> result=new ArrayList<>();
        while (rs.next()) {
            ArrayList<String> temp=new ArrayList<>();
            for(int i=0;i<col_name.length;i++){
                String col = rs.getString(col_name[i]);
                temp.add(col);
            }
            result.add(temp);
        }

        statement.close();
        connection.close();

        return result;
    }
    public static void saveExcel(ArrayList<ArrayList<String>> result,int sheet_number,String sheetname,String dbpath,String sql) throws IOException, WriteException, ClassNotFoundException, SQLException {
        if(result!=null){
            WritableSheet worksheet=workbook.createSheet(sheetname,sheet_number+1);
            //获取列名
            Class.forName("org.sqlite.JDBC");
            Connection connection= DriverManager.getConnection(dbpath);
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            ResultSetMetaData metaData=rs.getMetaData();
            String [] col_name=new String[metaData.getColumnCount()];
            for(int i=0;i<metaData.getColumnCount();i++){
                col_name[i]=metaData.getColumnName(i+1);
            }
            //获取列名结束
            //添加表头开始
            ArrayList<String> Colums=new ArrayList<>();

            for(int l=0;l<col_name.length;l++){
                Colums.add(col_name[l]);
            }

            for(int k=0;k<Colums.size();k++){
                String oneline_cell_string=Colums.get(k);
                Label oneline_cell=new Label(k,0,oneline_cell_string);
                worksheet.addCell(oneline_cell);
            }
            //添加表头结束

            //添加数据开始
            for(int i=0;i<result.size();i++){
                ArrayList<String> result_temp=result.get(i);
                for(int j=0;j<result_temp.size();j++){
                    String oneline_cell_string=result_temp.get(j);
                    Label oneline_cell=new Label(j,i+1,oneline_cell_string);
                    worksheet.addCell(oneline_cell);
                }
            }
            //添加数据结束
        }

    }
}
