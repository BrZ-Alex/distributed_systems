DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS nodes;

CREATE TABLE nodes(
                      id BIGINT NOT NULL PRIMARY KEY,
                      version INT NOT NULL,
                      uid INT NOT NULL,
                      users VARCHAR(256) NOT NULL,
                      changeset INT NOT NULL,
                      lat DOUBLE PRECISION NOT NULL,
                      lon DOUBLE PRECISION NOT NULL
);

CREATE TABLE tags(
                     node_id BIGINT NOT NULL REFERENCES nodes(id),
                     k VARCHAR(256) NOT NULL,
                     v VARCHAR(256) NOT NULL,
                     PRIMARY KEY (node_id, k)
);