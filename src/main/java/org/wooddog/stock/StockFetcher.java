package org.wooddog.stock;

import org.wooddog.IOUtil;
import org.wooddog.domain.Stock;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class StockFetcher {


    public StockFetcher() {
    }

    public List<Stock> getStocks() {
        List<Stock> stockList;
        String page;
        String stockTable;

        page = loadPage();
        stockTable = getStockTable(page);
        stockList = parseStockTable(stockTable);

        return stockList;
    }

    private String loadPage() {
        URL url;
        InputStream stream;
        byte[] content;

        try {
            url = new URL("http://borsen.dk/kurslister/danske_aktier/c20.html");
        } catch (MalformedURLException x) {
            throw new RuntimeException("url is malformed");
        }

        stream = openStream(url);

        try {
            content = IOUtil.read(stream);
        } finally {
            IOUtil.close("c20", stream);
        }

        return new String(content);
    }

    private InputStream openStream(URL url) {
        URLConnection connection;
        InputStream stream;

        try {
            connection = url.openConnection();
            connection.connect();
            stream = connection.getInputStream();
        } catch (IOException x) {
            throw new RuntimeException("unable to open " + url.toExternalForm(), x);
        }

        return stream;
    }

    private String getStockTable(String page) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("<div class=\"stock-table\">\\W*(<table.*?</table>)", Pattern.DOTALL);
        matcher = pattern.matcher(page);

        if (!matcher.find()) {
            throw new RuntimeException("no stock table found");
        }

        return matcher.group(1);
    }

    private List<Stock> parseStockTable(String stockTable) {
        List<Stock> stockList;
        Stock stock;
        HtmlTable table;
        Date date;

        stockList = new ArrayList<Stock>();
        table = new HtmlTable(stockTable);
        date = new Date();

        // skipping the header
        table.nextRow();

        while (table.nextRow()) {
            stock = new Stock();

            stock.setDate(date);

            table.nextCell();
            stock.setCompany(table.getValue());

            table.nextCell();
            stock.setDiff(table.getValue());

            // skipping diff amount
            table.nextCell();

            table.nextCell();
            stock.setValue(table.getValue());

            stockList.add(stock);
        }

        return stockList;
    }
}
