
import filters.*;
import org.junit.*;
import twitter4j.*;

import java.util.Date;

import static org.junit.Assert.*;


public class TestFilters {
    @Test
    public void testBasic() {
        Filter f = new BasicFilter("fred");
        assertTrue(f.matches(makeStatus("Fred Flintstone")));
        assertTrue(f.matches(makeStatus("fred Flintstone")));
        assertFalse(f.matches(makeStatus("Red Skelton")));
        assertFalse(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testNot() {
        Filter f = new NotFilter(new BasicFilter("fred"));
        assertFalse(f.matches(makeStatus("Fred Flintstone")));
        assertFalse(f.matches(makeStatus("fred Flintstone")));
        assertTrue(f.matches(makeStatus("Red Skelton")));
        assertTrue(f.matches(makeStatus("red Skelton")));
    }

    @Test
    public void testAnd(){
        /*tests the following expression*/
        // exp1 ^ exp2 ^ exp3 ^ \exp4 ^ \(exp5 ^ exp6)

        Filter exp1 = new BasicFilter("melody");
        Filter exp2 = new BasicFilter("mia");
        Filter exp3 = new BasicFilter("mira");
        Filter exp4 = new BasicFilter("mariam");
        Filter exp5 = new BasicFilter("rasha");
        Filter exp6 = new BasicFilter("emad");

        Filter exp12 = new AndFilter(exp1, exp2);
        Filter exp123 = new AndFilter(exp12, exp3);
        Filter notExp4 = new NotFilter(exp4);
        Filter exp56 = new AndFilter(exp5, exp6);
        Filter notExp56 = new NotFilter(exp56);
        Filter exp123not4 = new AndFilter(exp123, notExp4);
        Filter f = new AndFilter(exp123not4, notExp56);


        assertTrue(f.matches(makeStatus("melody, mira, mia are good cats and rasha feeds them")));

        assertTrue(f.matches(makeStatus("melody, mira and mia wants a slap on their face by emad")));
        assertFalse(f.matches(makeStatus("melody, mira and mia wants a slap on their face by mariam")));

        assertFalse(f.matches(makeStatus("melody, mira and mia wants a slap on their face by rasha and emad")));

        assertEquals(f.toString(), "((((melody and mia) and mira) and not mariam) and not (rasha and emad))");
    }
    @Test
    public void orTest(){
        Filter exp1 = new BasicFilter("melody");
        Filter exp2 = new BasicFilter("mira");
        Filter exp3 = new BasicFilter("mia");

        Filter exp12 = new OrFilter(exp1, exp2);
        Filter f = new OrFilter(exp12, exp3);


        assertTrue(f.matches(makeStatus("melody is a good cat")));
        assertTrue(f.matches(makeStatus("mira and mia are not that good")));
        assertTrue(f.matches(makeStatus("melody, mira and mia are cats")));
        assertFalse(f.matches(makeStatus("emad is my father")));

        assertEquals(f.toString(), "((melody or mira) or mia)");
    }

    private Status makeStatus(String text) {
        return new Status() {
            @Override
            public Date getCreatedAt() {
                return null;
            }

            @Override
            public long getId() {
                return 0;
            }

            @Override
            public String getText() {
                return text;
            }

            @Override
            public String getSource() {
                return null;
            }

            @Override
            public boolean isTruncated() {
                return false;
            }

            @Override
            public long getInReplyToStatusId() {
                return 0;
            }

            @Override
            public long getInReplyToUserId() {
                return 0;
            }

            @Override
            public String getInReplyToScreenName() {
                return null;
            }

            @Override
            public GeoLocation getGeoLocation() {
                return null;
            }

            @Override
            public Place getPlace() {
                return null;
            }

            @Override
            public boolean isFavorited() {
                return false;
            }

            @Override
            public boolean isRetweeted() {
                return false;
            }

            @Override
            public int getFavoriteCount() {
                return 0;
            }

            @Override
            public User getUser() {
                return null;
            }

            @Override
            public boolean isRetweet() {
                return false;
            }

            @Override
            public Status getRetweetedStatus() {
                return null;
            }

            @Override
            public long[] getContributors() {
                return new long[0];
            }

            @Override
            public int getRetweetCount() {
                return 0;
            }

            @Override
            public boolean isRetweetedByMe() {
                return false;
            }

            @Override
            public long getCurrentUserRetweetId() {
                return 0;
            }

            @Override
            public boolean isPossiblySensitive() {
                return false;
            }

            @Override
            public String getLang() {
                return null;
            }

            @Override
            public Scopes getScopes() {
                return null;
            }

            @Override
            public String[] getWithheldInCountries() {
                return new String[0];
            }

            @Override
            public long getQuotedStatusId() {
                return 0;
            }

            @Override
            public Status getQuotedStatus() {
                return null;
            }

            @Override
            public int compareTo(Status o) {
                return 0;
            }

            @Override
            public UserMentionEntity[] getUserMentionEntities() {
                return new UserMentionEntity[0];
            }

            @Override
            public URLEntity[] getURLEntities() {
                return new URLEntity[0];
            }

            @Override
            public HashtagEntity[] getHashtagEntities() {
                return new HashtagEntity[0];
            }

            @Override
            public MediaEntity[] getMediaEntities() {
                return new MediaEntity[0];
            }

            @Override
            public ExtendedMediaEntity[] getExtendedMediaEntities() {
                return new ExtendedMediaEntity[0];
            }

            @Override
            public SymbolEntity[] getSymbolEntities() {
                return new SymbolEntity[0];
            }

            @Override
            public RateLimitStatus getRateLimitStatus() {
                return null;
            }

            @Override
            public int getAccessLevel() {
                return 0;
            }
        };
    }
}
