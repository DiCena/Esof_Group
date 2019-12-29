package esof.projeto.services.filters;

import esof.projeto.models.BaseModel;

import java.util.Set;

public class AndFilter<BaseModel> implements FilterI<BaseModel> {

    private FilterI<BaseModel> filter;
    private FilterI<BaseModel> otherFilter;

    public AndFilter(FilterI<BaseModel> filter, FilterI<BaseModel> otherFilter) {
        this.filter = filter;
        this.otherFilter = otherFilter;
    }

    @Override
    public Set<BaseModel> filter(Set<BaseModel> entities) {
        Set<BaseModel> filter1=this.filter.filter(entities);
        return this.otherFilter.filter(filter1);
    }
}
