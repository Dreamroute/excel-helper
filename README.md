### 0、起步
```
<dependency>
    <groupId>com.github.dreamroute</groupId>
    <artifactId>excel-helper</artifactId>
    <version>newest version</version>
</dependency>
```

### 1、比较：
1. 简单：满足日常80%功能，不支持单元格合并、公式等特性；
1. 多样：可导出excel-2003/2007，提供文件、byte数组导出，也可直接创建Workbook之后自行操作；
1. 性能：工具内部大面积使用缓存，性能强大；
1. 质量：与互联网上其他同类型工具类相比，代码较优雅；
1. 方式：推荐使用xlsx方式，使用xls方式行数超过65535行可能造成OOM；
1. 更新：更新迅速，只要有人反馈哪里需要调整修改，合情合理的情况下立马修改，发布；

### 2、使用方式：
1. 定义实体类

    ```
    @Sheet(name = "DemoList")
    public class Demo {
        @Column(name = "姓名")
        private String name;
        @Column(name = "身高", cellType = CellType.NUMERIC)
        private BigDecimal height;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public BigDecimal getHeight() {
            return height;
        }
    
        public void setHeight(BigDecimal height) {
            this.height = height;
        }
    }
    ```
1. 导出

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
1. 导入

    ```
    @Test
    public void importTest() {
        String path = "d:/1.xlsx";
        ExcelType type = ExcelType.XLSX;
        List<User> users = ExcelHelper.importFromPath(ExcelType.XLS, path, User.class);
        System.err.println(users);
    }
    ```
1. 导出一共有3类：

> 1. ExcelHelper.exportFile()            // 导出到文件
> 1. ExcelHelper.exportByteArray()   // 导出到byte[]
> 1. ExcelHelper.exportWorkbook()   // 导出成为一个poi原生的Workbook

5. 导入一共有4类：

> 1. ExcelHelper.importFromPath
> 1. ExcelHelper.importFromFile
> 1. ExcelHelper.importFromByteArray
> 1. ExcelHelper.importFromInputStream

6. 相关注解说明：

> @Sheet：导出时候为sheet名称；
> @Column：定义列的相关属性；
> @Header：定义Header，也就是首行属性；
> @Cell：定义数据行属性；


### 更新日志：
>1. 1.0-RELEASE: 支持基础导出功能(仅支持xls导出，也就是excel-2003)；
>1. 1.1-RELEASE: 优化一些细节；
>1. 1.2-RELEASE: 支持xlsx(excel-2007)导出；
>1. 1.3-RELEASE: 支持xls和xlsx的导入功能；
>1. 1.4-RELEASE: 支持BigDecimal格式导入；
>1. 1.7-RELEASE: 处理个别小bug；
