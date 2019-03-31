package com.ortakat.kudos.backend.api.firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Service
public class FirebaseService {

	private Firestore firestoreDB;

	@PostConstruct
	public void init() throws Exception {

		FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
				.setCredentials(GoogleCredentials
						.fromStream(new ClassPathResource("rn-kudosb-board-firebase-adminsdk-3t3mt-c1305bcf0c.json").getInputStream()))
				.setProjectId("rn-kudosb-board")
				.build();

		firestoreDB = firestoreOptions.getService();

	}

	void saveData(BaseObject baseObject) throws Exception {
		if (Objects.isNull(baseObject)) {
			return;
		}
		if (!Objects.isNull(baseObject.getKey())) {
			firestoreDB.collection(baseObject.getCollection()).document(baseObject.getKey()).set(baseObject.getInputs());
			baseObject.setReferencePath(baseObject.getCollection() + "/" + baseObject.getKey() + "/");
			return;
		}
		ApiFuture<DocumentReference> future = firestoreDB.collection(baseObject.getCollection()).add(baseObject.getInputs());
		baseObject.setKey(future.get().getId());
		baseObject.setReferencePath(future.get().getPath() + "/");

	}

	List<QueryDocumentSnapshot> readData(String collection) throws Exception {
		ApiFuture<QuerySnapshot> querySnapshotApiFutures = firestoreDB.collection(collection).get();
		QuerySnapshot querySnapshotApiFuture = querySnapshotApiFutures.get();
		return querySnapshotApiFuture.getDocuments();
	}

	void deleteData(String collection, String key) {
		firestoreDB.collection(collection).document(key).delete();
	}


}
