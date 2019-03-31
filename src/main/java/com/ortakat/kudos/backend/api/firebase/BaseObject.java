package com.ortakat.kudos.backend.api.firebase;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class BaseObject {

	private String collection;

	private String referencePath;

	private String key;

	private Map<String, Object> inputs = new HashMap<>();

	private List<QueryDocumentSnapshot> allData = new ArrayList<>();

	private BaseObject(String collection) {
		this.collection = collection;
	}

	public static BaseObject getInstance(String collection) {
		return new BaseObject(collection);
	}


	public BaseObject addElement(String key, Object value) {
		inputs.put(key, value);
		return this;
	}

	public BaseObject addReferenceElement(String key, BaseObject value) {
		if (Objects.isNull(value.getKey())) {
			return this;
		}
		inputs.put(key, value.getKey());
		return this;
	}

	public BaseObject addReferenceElements(String key, List<BaseObject> values) {
		List<String> referenceIds = values.stream().map(e -> getReferencePath()).collect(Collectors.toList());
		return addElement(key, referenceIds);
	}


	public BaseObject save(FirebaseService firebaseService) throws Exception {
		firebaseService.saveData(this);
		return this;
	}

	public BaseObject read(FirebaseService firebaseService) throws Exception {
		allData = firebaseService.readData(collection);
		if (Objects.isNull(key)) {
			return this;
		}
		Optional<QueryDocumentSnapshot> filterelement = allData.stream().filter(e -> e.getId().equals(key)).findFirst();
		filterelement.orElseThrow(Exception::new);
		inputs = filterelement.get().getData();
		referencePath = filterelement.get().getReference().getPath() + "/";
		return this;
	}

	public BaseObject getChildObject(String collection) {
		return new BaseObject(getReferencePath() + collection);
	}


}
