package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Tracer;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public abstract class SpamProcessor implements Processor {
    private Deniable deniable;
    private Tracer tracer;
    private Set<Checker> checkerList;

    @SafeVarargs
    public SpamProcessor(Deniable deniable, Tracer tracer, Class<? extends SpamChecker>... checker) {
        this.deniable = deniable;
        this.tracer = tracer;
        this.checkerList = new LinkedHashSet<>();
        addChecker(checker);
    }

    @SafeVarargs
    protected final void addChecker(Class<? extends SpamChecker>... classes) {
        for (Class<? extends SpamChecker> cls : classes) {
            try {
                SpamChecker checker = cls.getConstructor(DeniableInfoAdapter.class).newInstance(deniable);
                System.out.println(cls.toString());
                checkerList.add(checker);
            } catch (Exception ex) {
                throw new IllegalArgumentException(cls.getName() + " is not valid spam checker.");
            }
        }
    }

    public boolean process() {
        for (Checker checker : checkerList) {
            tracer.setLastChecker(checker);
            tracer.setLastProcessor(this);
            try {
                tracer.setResult(checker.check());
            } catch (Exception ex) {
                tracer.setResult(Tracer.Result.ERROR);
                ex.printStackTrace();
            }
            if (true) {
                Static.consoleMsg(Lang.DEBUG.builder()
                        .addKey(Lang.Key.PROCESSOR_NAME, Lang.Key.CHECKER_NAME, Lang.Key.CHECKER_RESULT)
                        .addVal(this.name(), checker.name(), tracer.getResult().toString())
                        .build());
            }
            if (tracer.getResult() == Tracer.Result.FORCE_PASS) {
                return true;
            } else if (tracer.getResult() == Tracer.Result.DENY) {
                deniable.deny();
                return true;
            } else if (tracer.getResult() == Tracer.Result.ERROR) {
                Static.consoleMsg(Lang.ERROR.builder()
                        .single(Lang.Key.CHECKER_NAME, checker.name())
                        .build());
            }
        }
        return false;
    }

    @Override
    public String name() {
        return "SpamProcessor";
    }
}
