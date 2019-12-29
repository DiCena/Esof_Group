package esof.projeto.services.filters;

import esof.projeto.models.BaseModel;

import java.util.Set;

public interface FilterI<BaseModel> {
    Set<BaseModel> filter (Set<BaseModel> entities);
}
