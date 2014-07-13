package de.fuberlin.wiwiss.pubby.sources;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class PropertyFilteredDataSource implements DataSource {

	protected Set<String> filteredProperties = new HashSet<String>();
	protected DataSource wrappedSource = null;
	
	public PropertyFilteredDataSource(DataSource result, Set<String> set) {
		this.filteredProperties = set;
		this.wrappedSource = result;
	}

	@Override
	public boolean canDescribe(String absoluteIRI) {
		return this.wrappedSource.canDescribe(absoluteIRI);
	}

	@Override
	public Model describeResource(String absoluteIRI) {
		Model tempModel = this.wrappedSource.describeResource(absoluteIRI);
		Model resultModel = ModelFactory.createDefaultModel();
		for(String iri : this.filteredProperties) {
			Property prop = tempModel.getProperty(iri);
			resultModel.add(tempModel.listStatements((Resource) null, prop, (RDFNode) null));
		}
		return resultModel;
	}

	@Override
	public Map<Property, Integer> getHighIndegreeProperties(String resourceIRI) {
		return this.wrappedSource.getHighIndegreeProperties(resourceIRI);
	}

	@Override
	public Map<Property, Integer> getHighOutdegreeProperties(String resourceIRI) {
		return this.wrappedSource.getHighOutdegreeProperties(resourceIRI);
	}

	@Override
	public Model listPropertyValues(String resourceIRI, Property property,
			boolean isInverse) {
		// Adjust
		return this.wrappedSource.listPropertyValues(resourceIRI, property, isInverse);
	}

	@Override
	public List<Resource> getIndex() {
		return this.wrappedSource.getIndex();
	}

}
