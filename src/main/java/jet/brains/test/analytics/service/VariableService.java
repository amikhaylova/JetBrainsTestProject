package jet.brains.test.analytics.service;

import jet.brains.test.analytics.dto.UploadRequestDTO;
import jet.brains.test.analytics.entity.Template;
import jet.brains.test.analytics.entity.Variable;
import jet.brains.test.analytics.enums.DataType;
import jet.brains.test.analytics.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VariableService {
    @Autowired
    VariableRepository variableRepository;

    public boolean saveVariables(UploadRequestDTO dto, Template template) {
        List<Map<String, String>> variables = dto.getVariables();
        for (Map<String, String> map : variables) {
            Variable variable = new Variable();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                variable.setName(entry.getKey());
                variable.setType(DataType.valueOf(entry.getValue()));
                variable.setTemplate(template);
            }
            variableRepository.save(variable);
        }
        return true;
    }

    public boolean validateVariables(List<Variable> variablesToType, List<Map<String, String>> variablesValues) throws NumberFormatException{
        HashMap<String, String> namesToValues = new HashMap<>();
        for (Map<String, String> map : variablesValues) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                namesToValues.put(entry.getKey(), entry.getValue());
            }
        }
        for (Variable var : variablesToType) {
            if (namesToValues.containsKey(var.getName())) {
                DataType.dataTypeMap.get(var.getType()).apply(namesToValues.get(var.getName()));
            }
        }
        return true;
    }

}
