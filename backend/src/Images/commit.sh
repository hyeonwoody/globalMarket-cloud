WORK_DIR=$(cd $(dirname $0) && pwd)
DATE=$(date +"%s")
cd "${WORK_DIR}"
#a
if [ $# -eq 0 ]; then
	BRANCH="${DATE}_default"
	MSG="default"
else 
	BRANCH="$1"
	MSG="$2"
fi
pushd ${WORK_DIR}
git add .
git commit -m "${MSG}"
git push -f origin "${BRANCH}"
popd
exit 0
