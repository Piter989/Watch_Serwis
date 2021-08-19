package sample.table;

public class WatchTableModel {

    String idWatch, nameWatch, currentPrice, actuallyPrice, textArea, urlText;



    public WatchTableModel(String idWatch, String nameWatch, String currentPrice, String actuallyPrice, String textArea, String url, String urlText) {
        this.idWatch = idWatch;
        this.nameWatch = nameWatch;
        this.currentPrice = currentPrice;
        this.actuallyPrice = actuallyPrice;
        this.textArea = textArea;
        this.urlText = urlText;

    }

    public String getIdWatch() {
        return idWatch;
    }

    public void setIdWatch(String idWatch) {
        this.idWatch = idWatch;
    }

    public String getNameWatch() {
        return nameWatch;
    }

    public void setNameWatch(String nameWatch) {
        this.nameWatch = nameWatch;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getActuallyPrice() {
        return actuallyPrice;
    }

    public void setActuallyPrice(String actuallyPrice) {
        this.actuallyPrice = actuallyPrice;
    }

    public String getTextArea() {
        return textArea;
    }

    public void setTextArea(String textArea) {
        this.textArea = textArea;
    }

    public String getUrl() {
        return urlText;
    }

    public void setUrl(String urlText) {
        this.urlText = urlText;
    }
}

