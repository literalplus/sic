package li.l1t.sic.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represents a vote on a quote, for tracking who voted.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-05-07
 */
@Entity
@Table(name = "sic_quote_vote")
public class QuoteVote extends BaseEntity {
    @EmbeddedId
    private QuoteVoteId id;

    @Column(name = "is_up")
    private boolean isUpvote;

    public QuoteVote() {

    }

    public QuoteVote(Quote quote, User user, boolean isUpvote) {
        this.id = new QuoteVoteId(user, quote);
        this.isUpvote = isUpvote;
    }

    public QuoteVoteId getId() {
        return id;
    }

    public User getUser() {
        return getId().getUser();
    }

    public Quote getQuote() {
        return getId().getQuote();
    }

    public boolean isUpvote() {
        return isUpvote;
    }

    public void setUpvote(boolean upvote) {
        isUpvote = upvote;
    }

    public int getModifier() {
        return isUpvote() ? +1 : -1;
    }

    @Embeddable
    public static class QuoteVoteId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_name", updatable = false)
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "quote_id", updatable = false)
        private Quote quote;

        public QuoteVoteId() {

        }

        public QuoteVoteId(User user, Quote quote) {
            this.user = user;
            this.quote = quote;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Quote getQuote() {
            return quote;
        }

        public void setQuote(Quote quote) {
            this.quote = quote;
        }
    }
}
