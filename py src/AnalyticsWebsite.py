from .website import Website  # Assuming Website is defined in a separate module
from .excel_handler import ExcelHandler  # Assuming ExcelHandler is defined in a separate module

class AnalyticsWebsite(Website):
    def __init__(self, excel, sheet):
        self.handler = ExcelHandler(excel, sheet)
        super().__init__()

    def set_elements(self, doc):
        return doc.select("details#station-metadata table")

    def handle_frost_free(self, ff_table, row_index):
        self.iterate_table(row_index, ff_table, self.COLR, self.handler)

    def set_col_index(self):
        return self.COLR