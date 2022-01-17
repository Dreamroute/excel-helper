### 0. 起步

```
<dependency>
    <groupId>com.github.dreamroute</groupId>
    <artifactId>excel-helper</artifactId>
    <version>latest version</version>
</dependency>
```

### 1. 比较：

>1. 简单：满足日常80%功能，不支持单元格合并、公式等特性；
>1. 多样：可导出excel-2003/2007，提供文件、byte数组导出，也可直接创建Workbook之后自行操作；
>1. 性能：工具内部大面积使用缓存，性能强大；
>1. 质量：与互联网上其他同类型工具类相比，代码较优雅；
>1. 方式：推荐使用xlsx方式，使用xls方式行数超过65535行可能造成OOM；
>1. 更新：更新迅速，只要有人反馈哪里需要调整修改，合情合理的情况下立马修改，发布；

### 2. 使用方式：

1. 定义实体类

    ```
   @Data
    @Sheet(name = "DemoList")
    public class Demo {
        @Column(name = "姓名")
        private String name;
        @Column(name = "身高", cellType = CellType.NUMERIC)
        private BigDecimal height;
        @Column(name = "时间")
        // 支持日期类型转换，默认是将timestamp转换成'yyyy-MM-dd HH:mm:ss'类型
        @DateColumn
        private long time;
    }
    ```
    
2. 导出

    ```
    @Test
    public void sortListTest() {
        List<Demo> demoList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Demo demo = new Demo();
            demo.name = "w.dehai";
            demo.height = new BigDecimal("1.80342");
            demoList.add(demo);
        }
        ExcelHelper.exportFile(ExcelType.XLS, demoList, "d:/DemoList.xls");
    }
    ```

3. 导入

    ```
    @Test
    public void importTest() {
        String path = "d:/1.xlsx";
        ExcelType type = ExcelType.XLSX;
        List<User> users = ExcelHelper.importFromPath(ExcelType.XLS, path, User.class);
        System.err.println(users);
    }
    ```
4. 文件下载，直接在`Controller层使用download`方法进行文件下载
```
ExcelHelper.download(文件名, response, 数据);
```

6. 导出一共有3类：

    > 1. ExcelHelper.exportFile()
    > 1. ExcelHelper.exportByteArray()
    > 1. ExcelHelper.exportWorkbook()

7. 导入一共有4类：

    > 1. ExcelHelper.importFromPath
    > 1. ExcelHelper.importFromFile
    > 1. ExcelHelper.importFromByteArray
    > 1. ExcelHelper.importFromInputStream

8. 相关注解说明：

    > 1. @Sheet：导出时候为sheet名称；
    > 1. @Column：定义列的相关属性；
    > 1. @DateColumn：日期类型列转换类型，默认timestamp -> 'yyyy-MM-dd HH:mm:ss'
    > 1. @Header：定义Header，也就是首行属性；
    > 1. @Cell：定义数据行属性；
