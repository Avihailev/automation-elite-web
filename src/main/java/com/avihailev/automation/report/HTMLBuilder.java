package com.avihailev.automation.report;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.List;

public class HTMLBuilder {

    private StringBuilder table;
    private int columnCount;

    public HTMLBuilder(){

    }

    public void buildHTMLTable(String title, int columnCount, List<String> headersList, List<List<String>> data){
        this.columnCount = columnCount;
        table = new StringBuilder();
        if (title != null){
            if (!title.isEmpty()){
                table.append("<h5>").append(title).append("</h5>\n");
            }
        }
        table.append("<thead>");
        table.append("<tr>\n");
        for (int i = 0; i < columnCount; i++){
            table.append("<th style=\"max-width:150px; width:150\">");
            table.append(headersList.get(i));
            table.append("</th>\n");
        }
        table.append("</tr>\n");
        table.append("</thead>\n");
        table.append("<tbody>\n");
        for (List<String> datum : data) {
            addRow(datum);
        }
    }

    public void addRow(List<String> row){
        table.append("<tr>\n");
        for (int i = 0; i < this.columnCount; i++){
            table.append("<td>").append(row.get(i)).append("</td>\n");
        }
        table.append("<tr>\n");
    }

    public String getTable() {
        return addTableHeader() + table.toString() + addTableFooter();
    }

    private String addTableHeader(){
        return "<table style=\"width: 100%; border=\"5\"; cellpadding=\"3\">\n";
    }

    private String addTableFooter(){
        return "</tbody>\n </table>\n";
    }

    public static String codeDesigner(String code, CodeLanguage language){
        Markup markup = MarkupHelper.createCodeBlock(code, language);
        return markup.getMarkup();
    }

    public static String codeDesigner(String code){
        Markup markup = MarkupHelper.createCodeBlock(code);
        return markup.getMarkup();
    }
}
