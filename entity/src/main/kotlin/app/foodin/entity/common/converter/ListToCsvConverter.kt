package app.foodin.entity.common.converter

import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class ListToCsvConverter : AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        return attribute?.listToCsv()
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return dbData.csvToList()
    }
}
