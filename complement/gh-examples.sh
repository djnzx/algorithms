export GH_PAGER=""
for run_id in $(gh api -X GET    /repos/djnzx/algorithms/actions/runs -q '.workflow_runs[].id'); do
                gh api -X DELETE /repos/djnzx/algorithms/actions/runs/$run_id
done
