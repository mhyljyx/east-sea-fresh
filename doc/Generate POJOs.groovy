import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

// ==== 可配项 ====
packageName = "com.east.sea.pojo.entity"
typeMapping = [
        "bigint": "Long",
        "int": "Integer",
        "tinyint": "Integer",
        "varchar": "String",
        "text": "String",
        "datetime": "Date"
]
excludedFields = ["create_time", "update_time", "is_del"]
superClass = "pojo.BaseTablePropertyEntity"

// ==== 生成文件 ====
FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.findAll { it instanceof DasTable }.each { generate(it, dir) }
}

// ==== 主函数 ====
def generate(table, dir) {
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    def tableName = table.getName()

    new File(dir, className + ".java").withPrintWriter { out ->
        out.println "package ${packageName};"
        out.println ""
        out.println "import com.baomidou.mybatisplus.annotation.TableName;"
        out.println "import lombok.Data;"
        out.println "import ${superClass};"
        out.println ""
        out.println "@Data"
        out.println "@TableName(\"${tableName}\")"
        out.println "public class ${className}Entity extends ${simpleName(superClass)} {"
        out.println ""
        fields.each {
            out.println ""
            out.println "   private ${it.type} ${it.name};"
        }
        out.println ""
        out.println "}"
    }
}

// ==== 提取字段 ====
def calcFields(table) {
    DasUtil.getColumns(table).findAll { col ->
        !excludedFields.contains(col.getName().toLowerCase())
    }.collect { col ->
        def spec = col.getDasType().getSpecification().toLowerCase()
        def typeStr = typeMapping.find { p, t -> spec.contains(p) }?.value ?: "String"
        return [
                name: javaName(col.getName(), false),
                type: typeStr
        ]
    }
}

// ==== 命名规范函数 ====
def javaName(str, capitalize) {
    def words = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
    def result = words.join("")
    result = result[0].toLowerCase() + result[1]()
}