package org.wooddog.job.stock;

/**
* Created by IntelliJ IDEA.
* User: DENCBR
* Date: 15-08-11
* Time: 18:48
* To change this template use File | Settings | File Templates.
*/
class HtmlTable {
    private String table;
    private int pos;

    public HtmlTable(String table) {
        this.table = table;
    }

    public boolean nextRow() {
        pos = table.indexOf("<tr", pos + 3);

        return pos > -1;
    }

    public boolean nextCell() {
        int index;

        index = table.indexOf("<td", pos + 3);
        if (index > -1) {
            pos = index;
        }

        return index > -1;
    }

    public String getValue() {
        String cell;

        cell = table.substring(pos, table.indexOf("</td>", pos));
        cell = cell.replaceAll("\\n", "");
        cell = cell.replaceAll("\\<.*?>", "");
        cell = cell.trim();

        return cell;
    }
}
