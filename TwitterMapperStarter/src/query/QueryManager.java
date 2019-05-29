package query;
import twitter.LiveTwitterSource;
import twitter.TwitterSource;
import ui.ContentPanel;
import util.ColorSetter;

import javax.swing.*;
import java.util.*;


public class QueryManager implements Iterable<Query>{
    List<Query> queries;
    ColorSetter colorSetter;
    TwitterSource twitterSource;
    ContentPanel contentPanel;

    public QueryManager(ContentPanel contentPanel){
        queries = new ArrayList<>();
        twitterSource = new LiveTwitterSource();
        this.contentPanel = contentPanel;
       // this.colorSetter = colorSetter;
    }

    public Iterator iterator(){
        return queries.iterator();
    }

    /**
     * A new query has been entered via the User Interface
     * @param   query   The new query object
     */
    public void addQuery(Query query) {
        queries.add(query);
        Set<String> allterms = getQueryTerms();
        twitterSource.setFilterTerms(allterms);
        contentPanel.addQuery(query);
        twitterSource.subscribe(query);
    }

    // A query has been deleted, remove all traces of it
    public void terminateQuery(Query query) {
        // TODO: This is the place where you should disconnect the expiring query from the twitter source
        queries.remove(query);
        Set<String> allterms = getQueryTerms();
        twitterSource.setFilterTerms(allterms);
    }

    private Set<String> getQueryTerms() {
        Set<String> ans = new HashSet<>();
        for (Query q : queries) {
            ans.addAll(q.getFilter().terms());
        }
        return ans;
    }



}
