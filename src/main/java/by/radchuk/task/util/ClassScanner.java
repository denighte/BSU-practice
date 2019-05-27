package by.radchuk.task.util;

import by.radchuk.task.controller.ControllerException;

import java.io.IOException;
import java.util.Collection;

public class ClassScanner {
    public Collection<Class> scan(String clsPackage, Class annotationClass) throws ControllerException {
        try {
            return ClassReflections.builder().load(clsPackage)
                    .filter(annotationClass).get();

        } catch (IOException exception) {
            throw new ControllerException("Failed to scan the package!", exception);
        }
    }
}
