brew install gh

gh auth login

gh run list --limit 50 --json databaseId -q '.[].databaseId' | \
while read id; do
  gh run delete $id
done

gh auth logout
