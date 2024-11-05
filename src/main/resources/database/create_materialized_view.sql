CREATE MATERIALIZED VIEW IF NOT EXISTS projet_task_count_view AS (SELECT p.projet_id, p.name, p.description, COUNT(*) AS task_count FROM projet p
	LEFT JOIN task t ON t.projet_id = p.projet_id GROUP BY p.projet_id);

-- DROP MATERIALIZED VIEW projet_task_count_view;