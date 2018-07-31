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
1. 性能：工具内部大面积使用缓存，性能强大；
1. 质量：与互联网上其他同类型工具类相比，代码较优雅；
1. 方式：推荐使用xlsx方式，使用xls方式行数超过65535行可能造成OOM；
1. 更新：由于作者所在项目组许多地方使用文件导入导出，工具更新迅速；

### 2、使用方式：
1. 导出
   1. 定义实体类
       ```
       
    // name为导出excel文件的sheet名称
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
   2. b111
1. 导入
   1. a
   2. b


### 更新日志：
1. 1.0-RELEASE: 支持基础导出功能(仅支持xls导出，也就是excel-2003)；
1. 1.1-RELEASE: 优化一些细节；
1. 1.2-RELEASE: 支持xlsx(excel-2007)导出；
1. 1.3-RELEASE: 支持xls和xlsx的导入功能；
1. 1.4-RELEASE: 支持BigDecimal格式导入；
