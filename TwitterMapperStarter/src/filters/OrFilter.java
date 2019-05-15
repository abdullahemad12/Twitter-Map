package filters;

import twitter4j.Status;

import java.util.List;

public class OrFilter implements Filter {
    private final Filter child1;
    private final Filter child2;

    public OrFilter(Filter child1, Filter child2){
        this.child1 = child1;
        this.child2 = child2;
    }

    @Override
    public boolean matches(Status s) {
        return child1.matches(s) || child2.matches(s);
    }

    @Override
    public List<String> terms() {
        List<String> terms = child1.terms();
        terms.addAll(child2.terms());
        return terms;
    }

    public String toString(){
        return "(" + child1.toString() + " or " + child2.toString() + ")";
    }
}
