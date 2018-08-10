package main;

class WordCollection {
    String word;
    int totalWord;

    public WordCollection(String word, int totalWord) {
        this.word = word;
        this.totalWord = totalWord;
    }

    public WordCollection() {

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        // set to lowercase
        this.word = word.toLowerCase();
    }

    public int getTotalWord() {
        return totalWord;
    }

    public void setTotalWord(int totalWord) {
        this.totalWord = totalWord;
    }

    public String toString(){
        return "Word : "+this.word +"      "+"Total:"+ this.totalWord;
    }

}

