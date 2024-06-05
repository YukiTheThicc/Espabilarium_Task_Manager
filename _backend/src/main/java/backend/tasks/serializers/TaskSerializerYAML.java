package backend.tasks.serializers;

import backend.api.ITask;
import backend.api.ITaskStowage;
import backend.exceptions.EspRuntimeException;
import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TaskSerializerYaml
 *
 * @author Santiago Barreiro
 */
public class TaskSerializerYAML implements ITaskStowage.Serializer {

    // METHODS
    @Override
    public boolean serialize(String dir, ITask task) {
        try {
            File directory = new File(dir);
            if (!directory.exists()) directory.mkdir();
            if (directory.isDirectory()) {
                File targetFile = new File(dir + task.getUuid() + ".yaml");
                targetFile.createNewFile();
                if (targetFile.isFile()) {
                    FileWriter writer = new FileWriter(targetFile.getAbsoluteFile());
                    YamlMapping yamlData = Yaml.createYamlDump(task).dumpMapping();
                    writer.write(yamlData.toString());
                    writer.close();
                } else {
                    throw new EspRuntimeException("Could not find nor create the dataPath to save data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ITask deserialize(String file) {
        return null;
    }
}
