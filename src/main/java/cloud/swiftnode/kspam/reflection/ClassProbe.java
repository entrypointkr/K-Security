package cloud.swiftnode.kspam.reflection;

import cloud.swiftnode.kspam.abstraction.OSConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.BackslashToPeriodStringConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.BackslashToSlashStringConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.ClassRemoveStringConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.SlashToPeriodStringConvertor;
import cloud.swiftnode.kspam.storage.ReflectionStorage;
import cloud.swiftnode.kspam.util.OS;
import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-24.
 */
@SuppressWarnings("unchecked")
public class ClassProbe {
    private static OS os = new OSConvertor(SystemUtils.OS_NAME).convert();
    private Set<Class> classSet = new HashSet<>();

    // JUnit Test only
    public ClassProbe(ReflectionStorage storage) {
        try {
            String packageName = storage.getPackageName();
            ClassLoader loader = storage.getLoader();
            Enumeration<URL> enumUrl = loader.getResources(
                    new BackslashToSlashStringConvertor(packageName).convert());
            List<URL> urlList = new ArrayList<>();
            while (enumUrl.hasMoreElements()) {
                urlList.add(enumUrl.nextElement());
            }
            ClassLoader urlLoader = new URLClassLoader(urlList.toArray(new URL[0]));
            for (URL url : urlList) {
                File dir = new File(url.getFile());
                classSet.addAll(getClasses(urlLoader, dir.listFiles(), packageName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Set<Class> getClasses(ClassLoader loader, File[] files, String packageName) {
        Set<Class> classSet = new HashSet<>();
        for (File file : files) {
            if (file.isDirectory()) {
                classSet.addAll(getClasses(loader, file.listFiles(), packageName));
            } else if (file.getName().endsWith(".class")) {
                String periodPath;
                if (os == OS.WINDOWS) {
                    periodPath = new BackslashToPeriodStringConvertor(file.toString()).convert();
                } else {
                    periodPath = new SlashToPeriodStringConvertor(file.toString()).convert();
                }
                String parse = new ClassRemoveStringConvertor(periodPath.substring(periodPath.indexOf(packageName))).convert();
                try {
                    classSet.add(loader.loadClass(parse));
                } catch (Exception ex) {
                    // Ignore
                }
            }
        }
        return classSet;
    }

    public Set<Class> getClassesWithAnnotation(Class<? extends Annotation> annotation) {
        Set<Class> ret = new HashSet<>();
        for (Class clazz : classSet) {
            Class superClass = clazz.getSuperclass();
            if (clazz.isAnnotationPresent(annotation) || superClass != null &&
                    superClass.isAnnotationPresent(annotation)) {
                ret.add(clazz);
            }
        }
        return ret;
    }

    public Set<Class> getClassSet() {
        return classSet;
    }
}
