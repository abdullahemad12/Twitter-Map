
import org.junit.*;
import twitter.PlaybackTwitterSource;
import twitter4j.Status;

import java.util.HashSet;
import java.util.Set;
import observer.*;

import static org.junit.Assert.*;

/**
 * Test the basic functionality of the TwitterSource
 */
public class TestPlaybackTwitterSource {

    @Test
    public void testSetup() {
        PlaybackTwitterSource source = new PlaybackTwitterSource(1.0);
        TestObserver to = new TestObserver();
        // TODO: Once your TwitterSource class implements Observable, you must add the TestObserver as an observer to it here
        source.subscribe(to);
        source.setFilterTerms(set("food"));
        pause(3 * 1000);
        assertTrue(to.getNTweets() > 0);
        assertTrue(to.getNTweets() <= 10);
        int firstBunch = to.getNTweets();
        System.out.println("Now adding 'the'");
        source.setFilterTerms(set("food", "the"));
        pause(3 * 1000);
        assertTrue(to.getNTweets() > 0);
        assertTrue(to.getNTweets() > firstBunch);
        assertTrue(to.getNTweets() <= 10);
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private <E> Set<E> set(E ... p) {
        Set<E> ans = new HashSet<>();
        for (E a : p) {
            ans.add(a);
        }
        return ans;
    }
    private class TestObserver implements Observer {
        private int nTweets = 0;

        @Override
        public void update(Observable o, Object arg) {
            Status s = (Status) arg;
            nTweets ++;
        }

        public int getNTweets() {
            return nTweets;
        }
    }
}
