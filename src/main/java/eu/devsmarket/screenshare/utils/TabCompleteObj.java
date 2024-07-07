package eu.devsmarket.screenshare.utils;

public class TabCompleteObj {
    private int index;
    private String mainWord;
    private String[] tips;

    public TabCompleteObj(int index, String mainWord, String[] tips) {
        this.index = index;
        this.mainWord = mainWord;
        this.tips = tips;
    }

    public int getIndex() {
        return index;
    }

    public String getMainWord() {
        return mainWord;
    }

    public String[] getTips() {
        return tips;
    }
}
