package flashcards;

class Card {
    private final int cardNo;
    private String term;
    private String definition;

    public Card(int cardNo) {
        this.cardNo = cardNo;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNo=" + cardNo +
                ", term='" + term + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}