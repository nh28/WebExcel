from .website import Website  # Assuming Website is defined in a separate module
from .excel_handler import ExcelHandler  # Assuming ExcelHandler is defined in a separate module

class DataWebsite(Website):
    def __init__(self, excel, sheet):
        self.handler = ExcelHandler(excel, sheet)
        super().__init__()

    def set_elements(self, doc):
        return doc.select("details#normals-data table")

    def handle_frost_free(self, ff_table, row_index):
        global table_num
        if table_num == 0:
            self.iterate_table(row_index, ff_table, self.COLAK, self.handler)
            table_num += 1
        else:
            values = ff_table.select("td")
            index = 200
            for val in values:
                self.handler.write_excel(index, self.COLAK, val.text.strip())
                index += 1

    def set_col_index(self):
        return self.COLY #might create some problems