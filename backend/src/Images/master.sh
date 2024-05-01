WORK_DIR=$(cd $(dirname $0) && pwd)
cd "${WORK_DIR}"
#sd
pushd ${WORK_DIR}
git checkout master
git restore .
popd
exit 0
