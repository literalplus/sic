package li.l1t.sic.model.dto;

/**
 * Date Transfer Object for sic quotes, without person data.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
public class QuoteDto {
    private int id;
    private PersonDto person;
    private String text;
    private String creatorName;
    private String subText;
    private int voteCount;
    private int ownVote; //-1, 0 or +1, respectively

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getOwnVote() {
        return ownVote;
    }

    public void setOwnVote(int ownVote) {
        this.ownVote = ownVote;
    }
}
