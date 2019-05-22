**项目地址：**<https://github.com/neytao/sqlitetoExcel>

开发原因：使用sqliteStudio打开db数据库文件，导出格式仅支持HTML、JSON、PDF、SQL、XML，不包含Excel表格选项

使用语言：java

JAVA环境：jdk1.8

**引用包：**/lib/jxl.jar、/lib/sqlite-jdbc-3.27.2.1.jar 

**源代码：**/src/sqlsolve.java



**JAR编译文件：**

**目的包[可执行文件]：**/out/artifacts/sqliteSolvetoExcel_jar/sqliteSolvetoExcel.jar

在查询后得知jar可以发布为exe，使用exe4j工具打包即可 

注：由网上提示，打包exe时推荐jre使用32位版本，因此在exe目录中放置jre 1.8 x32位包

exe4j配置文件：/out/exe4jconfig.exe4j



**含JRE成品下载：**

​	含jre1.8 x32（新手点这里）：https://github.com/neytao/sqlitetoExcel/blob/master/sqlitetoxls_jre/sqlitetoxls_jre.zip

**不含JRE成品下载：**

​	无jre1.8 x32：https://github.com/neytao/sqlitetoExcel/blob/master/sqlitetoxls_without_jre/sqlitetoxls_without_jre.zip

​	注：需将本机jre 1.8 x32 文件夹复制到与exe相同目录

**使用方法：**

​	将需要转换为excel的db文件放置在本工具exe同一目录下即可。

​	